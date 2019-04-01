package controller;

import dao.DaoImpl;
import entity.Contact;
import jdk.nashorn.internal.ir.IfNode;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "controllerImpl")
@ViewScoped
public class ControllerImpl implements Serializable {

    private List<Contact> contactList;
    private DaoImpl dao;
    private Contact contact;

    public void create () {
        this.getDaoImpl().insert(this.contact);
        validationPassed();
        this.contact = new Contact();
    }

    public void update () {
        this.getDaoImpl().update(this.contact);
        validationPassed();
        this.contact = new Contact();
    }

    public void delete (Contact contact) {
        this.getDaoImpl().delete(contact);
        this.contact = new Contact();
    }

    public List<Contact> getContactList() {
        this.contactList = this.getDaoImpl().findAll();
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    private DaoImpl getDaoImpl() {
        if(this.dao == null)
            this.dao = new DaoImpl();
        return dao;
    }

    public Contact getContact() {
        if(this.contact == null)
            this.contact = new Contact();
        return contact;
    }

    public Contact getNewContact() {
        return new Contact();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void validationPassed() {
        if (this.contact.getName() != null && !this.contact.getName().isEmpty() &&
                this.contact.getSurname() != null && !this.contact.getSurname().isEmpty() &&
                this.contact.getPhoneNumber() != null && !this.contact.getPhoneNumber().isEmpty()) {
            RequestContext.getCurrentInstance().execute("PF('contactDialog').hide()");
        }
    }
}