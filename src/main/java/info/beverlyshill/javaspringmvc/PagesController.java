package info.beverlyshill.javaspringmvc;

import info.beverlyshill.javaspringmvc.dao.PagesDao;
import info.beverlyshill.javaspringmvc.domain.Pages;
import info.beverlyshill.javaspringmvc.util.Message;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application index page.
 * 
 * @author bhill2
 */
@Controller
public class PagesController {

	@Autowired
	private PagesDao pagesDao;

	@Autowired
	MessageSource messageSource;

	private String nameValue = "Index";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Selects the index view to render by returning its name.
	 * 
	 * @param locale
	 *            internationalization locale
	 * @param model
	 *            is the Spring model
	 * @return index view
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		List<Pages> pages = null;
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:datasource.xml");
		ctx.refresh();
		// get the dao object for Pages data
		PagesDao pagesDao = ctx.getBean("pagesDao", PagesDao.class);
		// Get all Pages records with specified name
		if (null == model || model.asMap().size() == 0) {
			try {
				pages = pagesDao.findAll(nameValue);
			} catch (Exception e) {
				model.addAttribute(
						"message",
						new Message("error", messageSource.getMessage(
								"findall_retrieve_fail", new Object[] {},
								locale)));
				logger.error("Error in findAll ", e.getMessage());
			}
		} else {
			try {
				pages = pagesDao.findAll(model.toString());
			} catch (Exception e) {
				model.addAttribute(
						"message",
						new Message("error", messageSource.getMessage(
								"findall_retrieve_fail", new Object[] {},
								locale)));
				logger.error("Error in findAll ", e.getMessage());
			}
		}
		// After retrieval see if no records were returned
		if (pages.size() == 0) {
			model.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"findall_retrieve_fail", new Object[] {}, locale)));
			logger.error("Error in findAll. No records were returned ");
		}
		// Log the number of Pags records retrieved
		logger.info("Pages record count: " + pages.size());
		// Add the Pages object to the model
		model.addAttribute("pages", pages);
		return "index";
	}

	public PagesDao getPagesDao() {
		return pagesDao;
	}

	public void setPagesDao(PagesDao pagesDao) {
		this.pagesDao = pagesDao;
	}
}
