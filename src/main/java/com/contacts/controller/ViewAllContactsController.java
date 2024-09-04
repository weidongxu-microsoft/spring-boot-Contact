package com.contacts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.contacts.entity.Contact;
import com.contacts.entity.ContactPhone;
import com.contacts.repository.ContactPhoneRepository;
import com.contacts.repository.ContactRepository;
import com.contacts.util.ContactWrapper;

@Controller
public class ViewAllContactsController {
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ContactPhoneRepository contactPhoneRepository;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping("/all_contacts")
	public String viewContacts(Model theModel,HttpServletRequest request)
	{
		List<Contact>  contactList=contactRepository.findByUserid((Long)httpSession.getAttribute("uid"));
		List<ContactPhone> phList=contactPhoneRepository.findByUserid((Long)httpSession.getAttribute("uid"));
		
		HashMap<Long,ContactWrapper> contacts=new HashMap<Long,ContactWrapper>();
		for(Contact ctemp : contactList)
		{
			ContactWrapper cw=new ContactWrapper();
			cw.setContactId(ctemp.getContactId());
			cw.setEmailId(ctemp.getEmailId());
			cw.setName(ctemp.getName());
			contacts.put(ctemp.getContactId(),cw);			
		}
		for(ContactPhone ctemp : phList)
		{
			ContactWrapper cw=contacts.get(ctemp.getContact().getContactId());
			cw.getPhList().add(ctemp.getPhoneNo());
			contacts.put(ctemp.getContact().getContactId(),cw);
		}
		
		theModel.addAttribute("clist", contacts);
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		  if (inputFlashMap != null) {
		    if(inputFlashMap.get("error")!=null);
		    {
		    	theModel.addAttribute("error",(String) inputFlashMap.get("error"));
		    }
		    // do the job
		  }

		
		return "view_contact";
	}

}
