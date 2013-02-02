package info.beverlyshill.javaspringmvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import info.beverlyshill.javaspringmvc.domain.Pages;
import info.beverlyshill.javaspringmvc.hibernate.dao.PagesDaoImpl;
import info.beverlyshill.javaspringmvc.dao.PagesDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 * Tests for PagesController class
 * 
 * @author bhill2
 * 
 */
public class PagesControllerTest extends AbstractControllerTest {

	private PagesDaoImpl pagesDao;

	private List<Pages> pagesList = new ArrayList<Pages>();

	private List<Pages> nullList = null;

	private PagesController pagesController = new PagesController();
	
	private String validNameValue = "Index";
	
	private String nullNameValue = null;

	@Before
	public void initPages() {
		// mock PagesDaoImpl dao implementation
		pagesDao = mock(PagesDaoImpl.class);
		// set the mocked pagesDao in pagesController
		pagesController.setPagesDao(pagesDao);
		// add the records for comparison
		this.addTestRecord("Index", "This is a sample web application built with the Spring framework.", "");
		this.addTestRecord("Index", "Development methodology is TDD with JUnit unit tests.", "");
		this.addTestRecord("Index", "Data is obtained from a lightweight embedded database server H2 via Hibernate ORM.", "");
		this.addTestRecord("Index", "Web pages are styled with CSS.", "");
		this.addTestRecord("Index", "log4j is used for logging.", "");	
	}

	/**
	 * Tests that when the index method is invoked from a GET request the
	 * expected view is returned
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPagesListView() throws Exception {
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String p = pagesController.index(null, uiModel, validNameValue);
		assertNotNull(p);
		assertEquals("Expected view was not returned.","index", p);
	}
	
	/**
	 * Tests that when the index method is invoked from a GET request
	 * with a null name value and the expected view is returned
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPagesListViewNullName() throws Exception {
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String p = pagesController.index(null, uiModel, null);
		assertNotNull(p);
		assertEquals("Expected view was not returned.","index", p);
	}

	/**
	 * Tests that the model returned with the view is what is expected
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPagesListModel() throws Exception {
		ExtendedModelMap uiModel = new ExtendedModelMap();
		pagesController.index(null, uiModel, validNameValue);
		@SuppressWarnings("unchecked")
		List<Pages> returnedPage = (List<Pages>) uiModel.get("pages");
		for(int i=0; i<returnedPage.size();i++) {
			assertEquals("Incorrect model data returned.", pagesList.get(i).getTextDesc(),returnedPage.get(i).getTextDesc());
		}
	}

	/**
	 * Tests that the controller collaborates correctly with the dao class it
	 * uses to obtain the domain data
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPagesListDomainData() throws Exception {
		given(pagesDao.findAll("Index")).willReturn(pagesList);
		for(int i=0; i<pagesList.size();i++) {
			assertEquals("Expected domain data was not returned.",pagesController.getPagesDao().findAll("Index").get(0).getTextDesc(),
					pagesList.get(0).getTextDesc());
		}
		verify(pagesDao, times(pagesList.size())).findAll("Index");
	}
	
	/**
	 * Test that the pagesDao is returned
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPagesDao() throws Exception {
		PagesDao pd = pagesController.getPagesDao();
		assertNotNull("The PagesDao from PagesController is null.",pd);
	}

	/**
	 * Tests that the view method throws an exception upon an error situation
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getPagesWithNullValue() throws Exception {
		given(pagesDao.findAll("null")).willReturn(nullList);
		assertEquals("Excpetion was expected but not thrown.",pagesController.getPagesDao().findAll("null").get(0).getName(), null);
		verify(pagesDao, times(1)).findAll("null");
	}
	
	/**
	 * Tests that the view displays error with 
	 * null Pages data
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void retrieveNullPagesAddsErrorMessageToModel() throws Exception {
		Model uiModel = new ExtendedModelMap();
		pagesController.retrievePagesData(null, uiModel, nullNameValue);
		uiModel = pagesController.getModel();
		assertTrue(uiModel.containsAttribute("message"));
	}
	
	/**
	 * Tests that the view does not display an error
	 * message with a valid Pages list
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrievePagesAddsNoErrorMessageToModel() throws Exception {
		Model uiModel = new ExtendedModelMap();
		pagesController.retrievePagesData(null, uiModel, validNameValue);
		uiModel = pagesController.getModel();
		assertNull(uiModel);
	}
	
	/**
	 * Tests that the view sets Pages data on model
	 * 
	 * @throws Exception
	 */
	@Test
	public void addsPagesToModel() throws Exception {
		Model uiModel = new ExtendedModelMap();
		pagesController.addDataToModel(uiModel, pagesList);
		uiModel = pagesController.getModel();
		assertTrue(uiModel.containsAttribute("pages"));
	}
	
	/**
	 * Adds a record of parameter values to the pagesList ArrayList
	 * @param name corresponding to name in pagesList
	 * @param description corresponding to textDesc in pagesList
	 * @param detailPage corresponding to detailPage in pagesList
	 */
	private void addTestRecord(String name, String description, String detailPage) {
		Pages newPage = new Pages();
		newPage.setName(name);
		newPage.setTextDesc(description);
		newPage.setDetailPage(detailPage);
		pagesList.add(newPage);
	}
}
