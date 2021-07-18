package com.uploads.controller;

import com.uploads.controller.request.ItemRequest;
import com.uploads.domain.Item;
import com.uploads.domain.UploadFile;
import com.uploads.repository.ItemRepository;
import com.uploads.service.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final FileUploader fileUploader;
    private final ItemRepository itemRepository;

    /**
     * 파일 업로드 폼 랜더링
     */
    @GetMapping("/items/new")
    public String showForm(Model model) {
        return "item-form";
    }

    /**
     * 첨부파일을 포함한 폼 데이터의 파일을 서버에 저장하고 해당 경로를 DB에 저장
     */
    @PostMapping("/items/new")
    public String form(
            @ModelAttribute ItemRequest itemRequest,
            RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileUploader.saveFile(itemRequest.getAttachFile());
        List<UploadFile> uploadFileList = fileUploader.saveFileList(itemRequest.getImageFiles());

        Item item = new Item();
        item.setItemName(itemRequest.getItemName());
        item.setAttachFile(attachFile);
        item.setUploadFileList(uploadFileList);

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());

        return "redirect:/items/{itemId}";
    }

    /**
     * 저장된 결과 랜더링
     */
    @GetMapping("/items/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "item-view";
    }

    /**
     * 전달받은 경로의 이미지를 바로 body에 삽입하여 전송
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileUploader.getFullPath(filename));
    }

    /**
     * 전달받은 itemId의 첨부파일 다운로드
     */
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttachFile(@PathVariable Long itemId) throws MalformedURLException {
        Item findItem = itemRepository.findById(itemId);

        String saveFileName = findItem.getAttachFile().getSaveFileName();
        String uploadFileName = findItem.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:"+fileUploader.getFullPath(saveFileName));
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
    /**
     * Content-Disposition
     * contents(body)가 웹 페이지의 일부인지 첨부되어 다운로드되어야 할 목적인지를 명시하기 위함
     * attachment; 로 설정하고 filename을 명시하면 해당 contents를 filename으로 다운로드
     */
}
