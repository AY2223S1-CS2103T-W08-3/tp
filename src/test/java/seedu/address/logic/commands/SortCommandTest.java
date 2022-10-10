package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedProfessors;
import static seedu.address.testutil.TypicalPersons.getSortedStudents;
import static seedu.address.testutil.TypicalPersons.getUnsortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedProfessors;
import static seedu.address.testutil.TypicalPersons.getUnsortedStudents;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/*
class SortCommandTest {

    private Model model = new ModelManager(
            getUnsortedAddressBook(), new UserPrefs());

    @Test
    void execute_sortUnfilteredList_success() {
        SortCommand sort = new SortCommand();
        Model expectedModel = new ModelManager(
                getSortedAddressBook(), new UserPrefs());
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(sort, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_sortProfessorsList_success() {
        SortCommand sort = new SortCommand();
        Model model = new ModelManager(getUnsortedProfessors(), new UserPrefs());
        Model expectedModel = new ModelManager(getSortedProfessors(), new UserPrefs());
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(sort, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_sortStudentsList_success() {
        SortCommand sort = new SortCommand();
        Model model = new ModelManager(getUnsortedStudents(), new UserPrefs());
        Model expectedModel = new ModelManager(getSortedStudents(), new UserPrefs());
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(sort, model, expectedMessage, expectedModel);
    }
}

 */