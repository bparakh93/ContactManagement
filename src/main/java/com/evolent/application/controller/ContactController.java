package com.evolent.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.evolent.application.entity.Contact;
import com.evolent.application.repository.ContactRepository;


@Controller
public class ContactController {

	@Autowired
	private Environment environment;
	private ContactRepository contactRepository;
	
	@Autowired
	public ContactController(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	
	@GetMapping("")
	public String loginPage() {
		return "login";
	}
	
	@PostMapping("login")
	public String validateLogin(@ModelAttribute(value = "userName") String userName,
			@ModelAttribute(value="password") String password,BindingResult result, Model model) {
		String uName = environment.getProperty("application.username");
		String pass = environment.getProperty("application.password");
		if(!(uName.equals(userName) && pass.equals(password))) {
			model.addAttribute("loginFailed",true);
			model.addAttribute("loginFailureMessage", new String("Wrong Username or Password"));
			return "login";
		}
		
		return "redirect:list";
	}
	
	@GetMapping("index")
	public String  baseForm(Model model) {
		model.addAttribute("contacts", this.contactRepository.findAll());
		return "index";
	}
	
	@GetMapping("addContact")
	public String showAddContactForm(Contact contact) {
		return "add-contact";
	}
	
	@GetMapping("list")
	public String retrieveContacts(Model model) {
		model.addAttribute("contacts", this.contactRepository.findAll(new Sort(Sort.Direction.ASC, "status")));
		return "index";
	}
	
	@PostMapping("add")
	public String addContact(@Valid Contact contact, BindingResult result, Model model) {
		if(result.hasErrors())
			return "add-contact";
		this.contactRepository.save(contact);
		return "redirect:list";
	}
	
	@GetMapping("add")
	public String addCountact(@Valid Contact contact,BindingResult result, Model model) {
		model.addAttribute("exception", new UnsupportedOperationException("Operation Not Supported."));
		return "errorPage";
	}
	
	@PostMapping("update/{id}")
	public String updateContact(@PathVariable("id") long id, @Valid Contact contact, BindingResult result, Model model) {
		contact.setId(id);
		if(result.hasErrors()) {
			return "update-contact";
		}	
		contactRepository.save(contact);
		model.addAttribute("contacts", this.contactRepository.findAll());
		return "index";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		try {
			Contact contact = this.contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Id :" + id));
			model.addAttribute("contact", contact);
		}
		catch(IllegalArgumentException exception) {
			model.addAttribute("exception", exception);
			return "errorPage";
		}
		return "update-contact";
	}
	
	@GetMapping("delete/{id}")
	public String deleteContact(@PathVariable ("id") long id, Model model) {
		try {
			Contact contact = this.contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact id : "+id));
			this.contactRepository.delete(contact);
			model.addAttribute("contacts",this.contactRepository.findAll());
		}
		catch(IllegalArgumentException exception) {
			model.addAttribute("exception", exception);
			return "errorPage";
		}
		return "index";		
	}
}
