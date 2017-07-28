package de.saxsys.mvvmfx.utils.items;

import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.scene.control.ChoiceBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class ChoiceBoxItemListTest {
	private Person person1 = new Person(1, "Hugo");
	private Person person2 = new Person(2, "Luise");
	private Person person3 = new Person(3, "Horst");
	private Person person4 = new Person(4, "Sabine");
	private Person person5 = new Person(5, "Manfred");

	private ItemList<Person, Integer> itemList;

	private ChoiceBox<Integer> choiceBox;

	@Before
	public void setup() {
		itemList = new ItemList<>(Person::getId);

		choiceBox = new ChoiceBox<>();
	}


	@Test
	public void testConnectToItemList() {
		// given
		itemList.getSourceList().addAll(person1, person2, person3, person4);
		assertThat(choiceBox.getItems()).isEmpty();
		assertThat(choiceBox.getValue()).isNull();
		assertThat(choiceBox.getSelectionModel().getSelectedItem()).isNull();

		// when
		itemList.connect(choiceBox);

		// then
		assertThat(choiceBox.getItems()).contains(1,2,3,4);
		assertThat(choiceBox.getValue()).isNull();
		assertThat(choiceBox.getSelectionModel().getSelectedItem()).isNull();
	}

	@Test
	public void testConnectToEmptyItemList() {
		// given
		assertThat(itemList.getSourceList()).isEmpty();

		// when
		itemList.connect(choiceBox);

		// then
		assertThat(choiceBox.getItems()).isEmpty();
	}

	@Test
	public void testItemListEntrysChange() {
		// given
		assertThat(itemList.getSourceList()).isEmpty();

		itemList.connect(choiceBox);

		// when
		itemList.getSourceList().addAll(person1, person2);

		// then
		assertThat(choiceBox.getItems()).containsExactly(1,2);

		// when
		itemList.getSourceList().add(person3);

		// then
		assertThat(choiceBox.getItems()).containsExactly(1,2,3);
	}

	@Test
	public void testItemIsSelectedFromItemList() {
		// given
		itemList.getSourceList().addAll(person1, person2, person3, person4, person5);

		itemList.connect(choiceBox);

		assertThat(choiceBox.getValue()).isNull();
		assertThat(choiceBox.getSelectionModel().getSelectedItem()).isNull();

		// when
		itemList.setSelectedItem(person2);

		// then
		assertThat(choiceBox.getValue()).isEqualTo(2);
		assertThat(choiceBox.getSelectionModel().getSelectedItem()).isEqualTo(2);
	}

	@Test
	public void testItemIsSelectedFromChoiceBox() {
		// given
		itemList.getSourceList().addAll(person1, person2, person3, person4, person5);

		itemList.connect(choiceBox);

		assertThat(itemList.getSelectedItem()).isNull();

		// when

		// cast needed to prevent selection based on index instead of id
		choiceBox.getSelectionModel().select((Integer) person2.getId());

		// then
		assertThat(itemList.getSelectedItem()).isEqualTo(person2);
	}

	@Test
	public void testNoSelection() {
		// given
		itemList.getSourceList().addAll(person1, person2, person3, person4);

		// when
		itemList.enableNoSelection(-1, "nothing");

		itemList.connect(choiceBox);

		FxTestingUtils.waitForUiThread(500);

		// then
		assertThat(choiceBox.getItems()).containsExactly(-1, 1, 2, 3, 4);
	}

}
