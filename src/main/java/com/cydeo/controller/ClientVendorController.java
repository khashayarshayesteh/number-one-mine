package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;
        public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }
    @GetMapping("/list")
    public String listAllClientVendor(Model model) {

        model.addAttribute("clientVendors", clientVendorService.listAllClientVendors());

        return "/clientVendor/clientVendor-list";
    }
    @GetMapping("/create")
    public String createClientVendor(Model model) {
        model.addAttribute(("newClientVendor"), new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "/clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String insertClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "/clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("update/{id}")
    public String editClientVendor(@PathVariable("id")Long id, Model model){
        model.addAttribute("clientVendor", clientVendorService.findById(id) );
        model.addAttribute("address", clientVendorService.findClientVendorAddress(id));
        model.addAttribute("clientVendorTypes", clientVendorService.findById(id).getClientVendorType());
        return "clientVendor/clientVendor-update";
    }


    @PostMapping("update/{id}")
    public  String updateClientVendor(@Valid  @ModelAttribute("id") ClientVendorDto clientVendorDto,BindingResult bindingResult, @PathVariable Long id , Model model){
        if (bindingResult.hasErrors()) {
    //        model.addAttribute("clientVendor", clientVendorService.findById(id) );
          model.addAttribute("address", clientVendorService.findClientVendorAddress(id));
          model.addAttribute("clientVendorTypes", clientVendorService.findById(id).getClientVendorType());
            return "clientVendor/clientVendor-update";
        }
        clientVendorService.updateClientVendor(clientVendorDto);
        return "redirect:/clientVendors/list";
    }





































}
