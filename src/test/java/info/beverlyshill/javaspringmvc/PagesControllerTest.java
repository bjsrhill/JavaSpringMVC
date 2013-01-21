package info.beverlyshill.javaspringmvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

	private Pages newPage = new Pages();

	private PagesController pagesController = new PagesController();

	@Before
	public void initPages() {
		// mock PagesDaoImpl dao implementation
		pagesDao = mock(PagesDaoImpl.class);
		pagesController.setPagesDao(pagesDao);
		newPage.setDetailPage("Test detail page");
		newPage.setName("Test");
		newPage.setTextDesc("Test description");
		pagesList.add(newPage);
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
		String p = pagesController.index(null, uiModel);
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
		String p = pagesController.index(null, uiModel);
		List<Pages> returnedPage = (List<Pages>) uiModel.get("pages");
		assertEquals("The expected model was not returned.",p.length(), returnedPage.size());
	}

	/**
	 * Tests that the controller collaborates correctly with the dao class it
	 * uses to obtain the domain data
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPagesListDomainData() throws Exception {
		given(pagesDao.findAll("Test")).willReturn(pagesList);
		assertEquals("Expected domain data was not returned.",pagesDao.findAll("Test").get(0).getName(),
				newPage.getName());
		verify(pagesDao, times(1)).findAll("Test");
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
		assertEquals("Excpetion was expected but not thrown.",pagesDao.findAll("null").get(0).getName(), null);
		verify(pagesDao, times(1)).findAll("null");
	}
}
