package adlere.train;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;

import org.junit.Test;

import adlere.train.commun.Constants;
import adlere.train.commun.Zone;
import adlere.train.exceptions.FunctionalException.ZoneNotFoundException;

public class ZoneTest {

	@Test
	public void getZoneByName() throws ZoneNotFoundException {
		assertEquals(Zone.ZONE1, Zone.getZoneByName(1));
		assertEquals(Zone.ZONE2, Zone.getZoneByName(2));
		assertEquals(Zone.ZONE3, Zone.getZoneByName(3));
		assertEquals(Zone.ZONE4, Zone.getZoneByName(4));
	}

	@Test
	public void ZoneNotFound() throws ZoneNotFoundException {
		assertThrows(ZoneNotFoundException.class, () -> Zone.getZoneByName(0));
	}

	@Test
	public void getPriceFromZone() {
		/* zone 1 */
		assertEquals(Zone.ZONE1.priceFrom(Zone.ZONE1), Constants.PRICE_WITHIN_ZONE1_AND_2);
		assertEquals(Zone.ZONE1.priceFrom(Zone.ZONE2), Constants.PRICE_WITHIN_ZONE1_AND_2);
		assertEquals(Zone.ZONE1.priceFrom(Zone.ZONE3), Constants.PRICE_FROM_1n2_TO_3);
		assertEquals(Zone.ZONE1.priceFrom(Zone.ZONE4), Constants.PRICE_FROM_1n2_TO_4);

		/* zone 2 */
		assertEquals(Zone.ZONE2.priceFrom(Zone.ZONE1), Constants.PRICE_WITHIN_ZONE1_AND_2);
		assertEquals(Zone.ZONE2.priceFrom(Zone.ZONE2), Constants.PRICE_WITHIN_ZONE1_AND_2);
		assertEquals(Zone.ZONE2.priceFrom(Zone.ZONE3), Constants.PRICE_FROM_1n2_TO_3);
		assertEquals(Zone.ZONE2.priceFrom(Zone.ZONE4), Constants.PRICE_FROM_1n2_TO_4);

		/* zone 3 */
		assertEquals(Zone.ZONE3.priceFrom(Zone.ZONE1), Constants.PRICE_FROM_1n2_TO_3);
		assertEquals(Zone.ZONE3.priceFrom(Zone.ZONE2), Constants.PRICE_FROM_1n2_TO_3);
		assertEquals(Zone.ZONE3.priceFrom(Zone.ZONE3), Constants.PRICE_WITHIN_ZONE3_AND_4);
		assertEquals(Zone.ZONE3.priceFrom(Zone.ZONE4), Constants.PRICE_WITHIN_ZONE3_AND_4);

		/* zone 4 */
		assertEquals(Zone.ZONE4.priceFrom(Zone.ZONE1), Constants.PRICE_FROM_1n2_TO_4);
		assertEquals(Zone.ZONE4.priceFrom(Zone.ZONE2), Constants.PRICE_FROM_1n2_TO_4);
		assertEquals(Zone.ZONE4.priceFrom(Zone.ZONE3), Constants.PRICE_WITHIN_ZONE3_AND_4);
		assertEquals(Zone.ZONE4.priceFrom(Zone.ZONE4), Constants.PRICE_WITHIN_ZONE3_AND_4);
	}

	@Test
	public void getListOfPossibleStations() {

		assertThat(Zone.getListOfPossibleZones("A"), is(Arrays.asList(Zone.ZONE1)));

		assertThat(Zone.getListOfPossibleZones("B"), is(Arrays.asList(Zone.ZONE1)));

		assertThat(Zone.getListOfPossibleZones("C"), is(Arrays.asList(Zone.ZONE2, Zone.ZONE3)));

		assertThat(Zone.getListOfPossibleZones("D"), is(Arrays.asList(Zone.ZONE2)));

		assertThat(Zone.getListOfPossibleZones("E"), is(Arrays.asList(Zone.ZONE2, Zone.ZONE3)));

		assertThat(Zone.getListOfPossibleZones("F"), is(Arrays.asList(Zone.ZONE3, Zone.ZONE4)));

		assertThat(Zone.getListOfPossibleZones("G"), is(Arrays.asList(Zone.ZONE4)));

		assertThat(Zone.getListOfPossibleZones("H"), is(Arrays.asList(Zone.ZONE4)));

		assertThat(Zone.getListOfPossibleZones("I"), is(Arrays.asList(Zone.ZONE4)));
	}

}
