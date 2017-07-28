package de.saxsys.mvvmfx.utils.newitemlist;

import de.saxsys.mvvmfx.utils.items.ItemList;
import javafx.collections.ListChangeListener;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemListTest {

    private Person person1 = new Person(1, "Hugo");
    private Person person2 = new Person(2, "Luise");
    private Person person3 = new Person(3, "Horst");
    private Person person4 = new Person(4, "Sabine");
    private Person person5 = new Person(5, "Manfred");

	ItemList<Person, Integer> itemList;

	@Before
	public void setup() {
		itemList = new ItemList<>(Person::getId);
	}

    @Test
    public void testAddRemove() {
        // given
        assertThat(itemList.getSelectedItem()).isNull();
        assertThat(itemList.getSourceList()).isNotNull().isEmpty();
        assertThat(itemList.getKeyList()).isNotNull().isEmpty();

        // when
        itemList.getSourceList().addAll(person1, person2, person3, person4);


        // then
        assertThat(itemList.getKeyList()).contains(1,2,3,4);


        // when
        itemList.getSourceList().add(person5);

        // then
        assertThat(itemList.getKeyList()).contains(1,2,3,4,5);

        // when
        itemList.getSourceList().remove(person3);

        // then
        assertThat(itemList.getKeyList()).contains(1,2,4,5);
    }


    @Test
    public void testListeners() {
        // given
        List<ListChangeListener.Change<? extends Integer>> keyChangeList = new ArrayList<>();
        itemList.getKeyList().addListener((ListChangeListener<Integer>) keyChangeList::add);


        assertThat(keyChangeList).isEmpty();


        // when

        itemList.getSourceList().add(person1);

        // then
        assertThat(keyChangeList).hasSize(1);
        ListChangeListener.Change<? extends Integer> keyChange1 = keyChangeList.get(0);
        assertThat(keyChange1.next()).isTrue();
        assertThat(keyChange1.wasAdded()).isTrue();
        assertThat(keyChange1.getAddedSize()).isEqualTo(1);
        assertThat(keyChange1.next()).isFalse();

        // when
        itemList.getSourceList().addAll(person2, person3);

        // then
        assertThat(keyChangeList).hasSize(2);
        ListChangeListener.Change<? extends Integer> keyChange2 = keyChangeList.get(1);
        assertThat(keyChange2.next()).isTrue();
        assertThat(keyChange2.wasAdded()).isTrue();
        assertThat(keyChange2.getAddedSize()).isEqualTo(2);
        assertThat(keyChange2.next()).isFalse();

        // when
        itemList.getSourceList().remove(person2);


        // then
        assertThat(keyChangeList).hasSize(3);
        ListChangeListener.Change<? extends Integer> keyChange3 = keyChangeList.get(2);
        assertThat(keyChange3.next()).isTrue();
        assertThat(keyChange3.wasRemoved()).isTrue();
        assertThat(keyChange3.getRemovedSize()).isEqualTo(1);
        assertThat(keyChange3.next()).isFalse();

    }


    @Test
    public void testReplaceModelItems() {
        // given
        itemList.getSourceList().addAll(person1, person2, person3, person4);

        // when
        List<Person> otherOrderingPersons = new ArrayList<>();
        otherOrderingPersons.add(person3);
        otherOrderingPersons.add(person1);
        otherOrderingPersons.add(person4);
        otherOrderingPersons.add(person2);
        otherOrderingPersons.add(person5);

        itemList.replaceModelItems(otherOrderingPersons);

        // then
        assertThat(itemList.getKeyList()).containsExactly(3,1,4,2,5);
    }


    /**
     * If an item is selected, it will also be selected after the items are replaced,
     * if it is still contained in the list.
     */
    @Test
    public void testReplaceModelItemsWithSelection() {
        // given
        itemList.getSourceList().addAll(person1, person2, person4);

        itemList.setSelectedItem(person2);

        // when
        List<Person> otherOrderingPersons = new ArrayList<>();
        otherOrderingPersons.add(person3);
        otherOrderingPersons.add(person1);
        otherOrderingPersons.add(person4);
        otherOrderingPersons.add(person2);

        itemList.replaceModelItems(otherOrderingPersons);

        // then
        assertThat(itemList.getSelectedItem()).isEqualTo(person2);

        // when

        // person2 is not part of this list
        List<Person> yetAnotherOrderingPersons = new ArrayList<>();
        yetAnotherOrderingPersons.add(person5);
        yetAnotherOrderingPersons.add(person4);
        yetAnotherOrderingPersons.add(person3);

        itemList.replaceModelItems(yetAnotherOrderingPersons);

        // then
        assertThat(itemList.getSelectedItem()).isNull();
    }

    @Test
	public void testKeyList() {
    	// given
		assertThat(itemList.getSourceList()).isEmpty();

		// then
		assertThat(itemList.getKeyList()).isEmpty();

		// when
		itemList.getSourceList().addAll(person1, person2, person3, person4);

		// then
		assertThat(itemList.getKeyList()).containsExactly(1,2,3,4);
	}

	@Test
	public void testLabelListWithoutLabelFunction() {
    	// given
		assertThat(itemList.getSourceList()).isEmpty();

		// then
		assertThat(itemList.getLabelList()).isEmpty();

		// when
		itemList.getSourceList().addAll(person1, person2, person3);

		// then
		assertThat(itemList.getLabelList()).containsExactly(person1.toString(), person2.toString(), person3.toString());

		// when
		itemList.getSourceList().remove(person2);

		// then
		assertThat(itemList.getLabelList()).containsExactly(person1.toString(), person3.toString());
	}

	@Test
	public void testLabelListWithLabelFunction() {
    	// given
		itemList.setLabelFunction(person -> "P:" + person.getName().toLowerCase());

		assertThat(itemList.getSourceList()).isEmpty();

		// then
		assertThat(itemList.getLabelList()).isEmpty();

		// when
		itemList.getSourceList().addAll(person1, person2, person3);

		// then
		assertThat(itemList.getLabelList()).containsExactly(
				"P:hugo",
				"P:luise",
				"P:horst"
		);

		// when
		itemList.getSourceList().remove(person2);

		// then
		assertThat(itemList.getLabelList()).containsExactly(
				"P:hugo",
				"P:horst"
		);
	}
}
