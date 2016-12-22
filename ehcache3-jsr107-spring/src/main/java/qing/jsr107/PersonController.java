package qing.jsr107;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController {

	@Autowired PersonService personService;

    @RequestMapping("/person/{ssn}")
    public @ResponseBody String getPerson(@PathVariable("ssn") String ssn){
        return personService.getPerson(ssn).toString();
    }
    
    @RequestMapping("/person/clearEntry/{ssn}")
    public @ResponseBody void clearEntry(@PathVariable("ssn") String ssn){
        personService.clearEntryFromCache(ssn);
    }
    
    @RequestMapping("/person/update/{ssn}")
    public @ResponseBody void update(@PathVariable("ssn") String ssn){
    	Person p = new Person("11", "ม๘วเ", "ม๘");
        personService.update(ssn,p);
    }
}
