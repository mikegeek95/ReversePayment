package com.bbva.kmic.lib.r060.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bbva.apx.exception.db.IncorrectResultSizeException;
import com.bbva.apx.exception.db.NoResultException;
import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.ThreadContext;
import com.bbva.elara.utility.jdbc.JdbcUtils;
import com.bbva.kmic.dto.commonmodel.Pagination;
import com.bbva.kmic.dto.commonmodel.Period;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.movementmodel.MicroloanMovementFilter;
import com.bbva.kmic.lib.r060.impl.utils.Constants;
import com.bbva.kmic.lib.r060.impl.utils.Mapper;
import com.bbva.kmic.lib.r060.impl.utils.Utils;

public class KMICR060ImplTest {

	/*
	 * There are methods of the APX Architecture that require greater complexity to
	 * mock, for this reason an instance of the class to be tested can be created
	 * with the overwritten methods and on these methods the mocking of the classes
	 * is carried out, for example Header data (The Mocking the header is only for
	 * libraries that are used online, in batch it would not work)
	 *
	 * Import section: - import
	 * com.bbva.elara.domain.transaction.RequestHeaderParamsName; - import
	 * com.bbva.elara.domain.transaction.request.header.CommonRequestHeader;
	 *
	 * Instance section:
	 *
	 * @Mock private CommonRequestHeader commonRequestHeader;
	 *
	 * @InjectMocks private KMICR060Impl kmicR060 = new KMICR060Impl() {
	 *
	 * @Override protected CommonRequestHeader getRequestHeader() { return
	 * commonRequestHeader; } };
	 */
	@InjectMocks
	private KMICR060Impl kmicR060;
	@Mock
	private JdbcUtils jdbcUtils;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ThreadContext.set(new Context());
	}

	@Test
	public void testList() {
		// Inputs
		final MicroloanMovementFilter input = new MicroloanMovementFilter();
		input.setPagination(new Pagination());
		input.setPeriod(new Period());
		input.getPagination().setFirstRow(1);
		input.getPagination().setPageSize(4000);
		final Map<String, Object> params = Utils.buildListParams(input);
		// Mocks
		when(jdbcUtils.pagingQueryForList(Constants.QUERIES.LIST, input.getPagination().getFirstRow(),
				input.getPagination().getPageSize(), params)).thenReturn(TestUtils.mockListFull());
		// Invoke
		final List<MicroloanMovement> output = kmicR060.executeListMicroloansMovements(input);
		// Asserts
		assertNotNull(output);
		assertFalse(output.isEmpty());
		output.forEach(movement -> {
			assertNotNull(movement);
		});
	}

	@Test
	public void testEmptyList() {
		// Inputs
		final MicroloanMovementFilter input = new MicroloanMovementFilter();
		input.setPagination(new Pagination());
		input.setPeriod(new Period());
		input.getPagination().setFirstRow(4001);
		input.getPagination().setPageSize(4000);
		final Map<String, Object> params = Utils.buildListParams(input);
		// Mocks
		when(jdbcUtils.pagingQueryForList(Constants.QUERIES.LIST, input.getPagination().getFirstRow(),
				input.getPagination().getPageSize(), params)).thenReturn(null);
		// Invoke
		final List<MicroloanMovement> output = kmicR060.executeListMicroloansMovements(input);
		// Asserts
		assertNotNull(output);
		assertTrue(output.isEmpty());
	}

	@Test
    @SuppressWarnings("unchecked")
	public void testCreate() {
		// Inputs
        final List<MicroloanMovement> input = TestUtils.mockListMovements(20);
        final Map<String, Object>[] mapMM = input.stream().map(Mapper::hashMicroloanMovement).toArray(Map[]::new);
		// Mocks
        when(jdbcUtils.batchUpdate(Constants.QUERIES.INSERT, mapMM))
                .thenReturn(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
		// Invoke
        assertEquals(10, kmicR060.executeCreateMicroloanMovements(input));
	}

	@Test
	public void testGet() {
		// Inputs
		final MicroloanMovement input = new MicroloanMovement();
		final Map<String, Object> mapMM = Mapper.hashMicroloanMovement(input);
		// Mocks
		when(jdbcUtils.queryForMap(Constants.QUERIES.SELECT, mapMM)).thenReturn(TestUtils.mockBeanFull());
		// Invoke
		final MicroloanMovement output = kmicR060.executeGetMicroloanMovement(input);
		// Asserts
		assertNotNull(output);
		assertNotNull(output.getAccount());
		assertNotNull(output.getAccount().getEvent());
		assertNotNull(output.getAmount());
		assertNotNull(output.getRelatedContract());
	}

    @Test
    public void testGetDetail() {
        // Inputs
        final MicroloanMovement input = new MicroloanMovement();
        input.setInterbankTrackingDescription(RandomStringUtils.randomAlphanumeric(12).toUpperCase());
        final Map<String, Object> mapMM = Mapper.hashMicroloanMovement(input);
        // Mocks
        final Map<String, Object> outmapMM = TestUtils.mockBeanFull();
        outmapMM.put("GF_TRACKING_TRANSACTION_DESC", input.getInterbankTrackingDescription());
        when(jdbcUtils.queryForMap(Constants.QUERIES.SELECT_DETAIL, mapMM)).thenReturn(outmapMM);
        // Invoke
        final MicroloanMovement output = kmicR060.executeGetMicroloanMovement(input);
        // Asserts
        assertNotNull(output);
        assertNotNull(output.getAccount());
        assertNotNull(output.getAccount().getEvent());
        assertNotNull(output.getAmount());
        assertNotNull(output.getRelatedContract());
        assertEquals(input.getInterbankTrackingDescription(), output.getInterbankTrackingDescription());
    }

    @Test
	@SuppressWarnings("unchecked")
	public void testNoResult() {
		// Inputs
		final MicroloanMovement input = new MicroloanMovement();
		final Map<String, Object> mapMM = Mapper.hashMicroloanMovement(input);
		// Mocks
		when(jdbcUtils.queryForMap(Constants.QUERIES.SELECT, mapMM)).thenThrow(NoResultException.class);
		// Invoke
		assertNull(kmicR060.executeGetMicroloanMovement(input));
	}
    
    @Test
    @SuppressWarnings("unchecked")
    public void testMultipleResult() {
        // Inputs
        final MicroloanMovement input = new MicroloanMovement();
        final Map<String, Object> mapMM = Mapper.hashMicroloanMovement(input);
        // Mocks
        when(jdbcUtils.queryForMap(Constants.QUERIES.SELECT, mapMM)).thenThrow(IncorrectResultSizeException.class);
        // Invoke
        assertNotNull(kmicR060.executeGetMicroloanMovement(input));
    }
}
