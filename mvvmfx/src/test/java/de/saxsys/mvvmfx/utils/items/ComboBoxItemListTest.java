package de.saxsys.mvvmfx.utils.items;

import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.scene.control.ComboBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class ComboBoxItemListTest {

    private Person person1 = new Person(1, "Hugo");
    private Person person2 = new Person(2, "Luise");
    private Person person3 = new Person(3, "Horst");
    private Person person4 = new Person(4, "Sabine");
    private Person person5 = new Person(5, "Manfred");

    private ItemList<Person, Integer> itemList;

    private ComboBox<Integer> comboBox;

    @Before
    public void setup() {
        itemList = new ItemList<>(Person::getId);
        comboBox = new ComboBox<>();
    }

    @Test
    public void testConnectToItemList() {
        // given
        itemList.getSourceList().addAll(person1, person2, person3, person4);
        assertThat(comboBox.getItems()).isEmpty();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();

        // when
        itemList.connect(comboBox);

        // then
        assertThat(comboBox.getItems()).contains(1,2,3,4);
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
    }

    @Test
    public void testConnectToEmptyItemList() {
        // given
        assertThat(itemList.getSourceList()).isEmpty();

        // when
        itemList.connect(comboBox);

        // then
        assertThat(comboBox.getItems()).isEmpty();
    }

    @Test
    public void testItemListEntrysChange() {
        // given
        assertThat(itemList.getSourceList()).isEmpty();

        itemList.connect(comboBox);

        // when
        itemList.getSourceList().addAll(person1, person2);

        // then
        assertThat(comboBox.getItems()).containsExactly(1,2);

        // when
        itemList.getSourceList().add(person3);

        // then
        assertThat(comboBox.getItems()).containsExactly(1,2,3);
    }

    @Test
    public void testItemIsSelectedFromItemList() {
        // given
        itemList.getSourceList().addAll(person1, person2, person3, person4, person5);

        itemList.connect(comboBox);

        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();

        // when
        itemList.setSelectedItem(person2);

        // then
        assertThat(comboBox.getValue()).isEqualTo(2);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(2);
    }

    @Test
    public void testItemIsSelectedFromComboBox() {
        // given
        itemList.getSourceList().addAll(person1, person2, person3, person4, person5);

        itemList.connect(comboBox);

        assertThat(itemList.getSelectedItem()).isNull();

        // when

        // cast needed to prevent selection based on index instead of id
        comboBox.getSelectionModel().select((Integer) person2.getId());

        // then
        assertThat(itemList.getSelectedItem()).isEqualTo(person2);
    }

    @Test
    public void testNoSelection() {
        // given
        itemList.getSourceList().addAll(person1, person2, person3, person4);

        // when
        itemList.enableNoSelection(-1, "nothing");

        itemList.connect(comboBox);

        FxTestingUtils.waitForUiThread(500);

        // then
        assertThat(comboBox.getItems()).containsExactly(-1, 1, 2, 3, 4);
    }


}
