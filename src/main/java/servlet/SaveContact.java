package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 22.02.15
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class SaveContact extends Command {
    private final static Logger logger= LogManager.getLogger(SaveContact.class);
    @Override
    public void process() {
        boolean newContact = "null".equals(req.getParameter("id"));
        System.out.println("Phones: " + req.getParameter("phones"));
        System.out.println("Ids: " + req.getParameter("ids"));
        String idsParameter = req.getParameter("ids");
        if (StringUtils.isNotEmpty(idsParameter))    {
            String[] ids = idsParameter.split("#\t");
            ArrayList<Integer> notRemove = new ArrayList<Integer>();
            for (int i = 0; i<ids.length; ++i) {
                notRemove.add(Integer.valueOf(ids[i]));
            }
        }
        String[] newPhones = req.getParameter("phones").split("#\t");
        String photo = req.getParameter("photo");

        String[] countries = new String[newPhones.length];
        String[] operators = new String[newPhones.length];
        String[] numbers = new String[newPhones.length];

        ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();

        String[] comments = req.getParameter("comments").split("#\t");
        String[] types = req.getParameter("types").split("#\t");

         if (StringUtils.isNotEmpty(newPhones[0])) {
        for (int i = 0; i<numbers.length; ++i) {
            countries[i]=StringUtils.substringBefore(newPhones[i], "(");
            operators[i]=StringUtils.substringBetween(newPhones[i], "(", ")");
            numbers[i]=StringUtils.substringAfter(newPhones[i], ")");

            phones.add(new PhoneNumber(numbers[i], types[i], comments[i], countries[i], operators[i]));
        }
         }
        //System.out.println(Arrays.toString((req.getParameter("phones")).split("\t")));
        Contact contact = newContact ? new Contact() : DataAccessObject.getFromDatabase(Integer.valueOf(req.getParameter("id")));
        contact.setName(req.getParameter("name"));
        contact.setSurname(req.getParameter("surname"));
        contact.setParentName(StringUtils.isEmpty(req.getParameter("fathername")) ? null :req.getParameter("fathername"));
        try {
        contact.setBirthday(new LocalDate(Integer.valueOf(req.getParameter("year")), Integer.valueOf(req.getParameterValues("months")[0]), Integer.valueOf(req.getParameter("day"))));
        }catch (NumberFormatException ex) {
            //a message
        }
    //    contact.setBirthday(Date.valueOf(req.getParameter("birthday")));
        contact.setJob(req.getParameter("job"));
        contact.setCitizenship(req.getParameter("citizenship"));
//        if (!isValidEmail(req.getParameter("email"))) {
//
//        }
        contact.setEmail(req.getParameter("email"));
        contact.setGender(req.getParameter("female") == null? Contact.Gender.MALE : Contact.Gender.FEMALE);
        Integer house = StringUtils.isNotEmpty(req.getParameter("house")) ? Integer.valueOf(req.getParameter("house")) : null;
        Integer place = StringUtils.isNotEmpty(req.getParameter("place")) ? Integer.valueOf(req.getParameter("[lace")) : null;
        Integer postIndex = StringUtils.isNotEmpty(req.getParameter("postIndex")) ? Integer.valueOf(req.getParameter("postIndex")) : null;
        Address address = new Address(req.getParameter("country"),  req.getParameter("town"), req.getParameter("street"),
                house, place, postIndex);
        contact.setAddress(address);
        int id = DataAccessObject.saveToDatabase(contact);
        //contact.setId(DataAccessObject.saveToDatabase(contact));
        if (!"null".equals(photo) && StringUtils.isNotEmpty(photo)) {
            DataAccessObject.setPhoto(photo, id);
        }
        DataAccessObject.addPhones(id, phones);
        try {
            res.sendRedirect("index.jsp");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    private boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return true;
        }
        Pattern correct =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = correct.matcher(email);
        return matcher.find();
    }
}
