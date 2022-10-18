package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalStudents.*;
import static seedu.address.testutil.TypicalTuitionClasses.TUITIONCLASS1;
import static seedu.address.testutil.TypicalTuitionClasses.TUITIONCLASS2;
import static seedu.address.testutil.TypicalTutors.*;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tuitionclass.TuitionClass;

import java.util.List;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AssignCommand.
 */
public class AssignCommandTest {

    @Test
    public void executeForStudent_validIndexAndTuitionClass_Success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(STUDENT3);
        model.addTuitionClass(TUITIONCLASS1);
        List<TuitionClass> expectedTuitionClasses = STUDENT3.getTuitionClasses();
        expectedTuitionClasses.add(TUITIONCLASS1);
        try {
            AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS1.getName());
            CommandResult commandResult = assignCommand.execute(model);
            assertEquals(commandResult, String.format(AssignCommand.MESSAGE_ASSIGN_STUDENT_SUCCESS, STUDENT3));
            assertEquals(expectedTuitionClasses, STUDENT3.getTuitionClasses());
        } catch (CommandException e) {

        }
    }

    @Test
    public void executeForTutor_validIndexAndTuitionClass_Success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(TUTOR3);
        model.addTuitionClass(TUITIONCLASS2);
        List<TuitionClass> expectedTuitionClasses = TUTOR3.getTuitionClasses();
        expectedTuitionClasses.add(TUITIONCLASS2);
        try {
            AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS2.getName());
            CommandResult commandResult = assignCommand.execute(model);
            assertEquals(commandResult, String.format(AssignCommand.MESSAGE_ASSIGN_STUDENT_SUCCESS,TUTOR3));
            assertEquals(expectedTuitionClasses, TUTOR3.getTuitionClasses());
        } catch (CommandException e) {

        }
    }

    @Test
    public void executeForStudent_invalidCurrentList_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(STUDENT3);
        model.addTuitionClass(TUITIONCLASS1);
        model.updateCurrentListType(Model.ListType.TUITIONCLASS_LIST);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS1.getName());

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_INVALID_CURRENT_LIST);
    }

    @Test
    public void executeForTutor_invalidCurrentList_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(TUTOR3);
        model.addTuitionClass(TUITIONCLASS1);
        model.updateCurrentListType(Model.ListType.TUITIONCLASS_LIST);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS1.getName());

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_INVALID_CURRENT_LIST);
    }

    @Test
    public void executeForStudent_invalidIndexFilteredList_throwsCommandException() {

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(STUDENT3);
        model.addTuitionClass(TUITIONCLASS1);
        model.updateCurrentListType(Model.ListType.STUDENT_LIST);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex, TUITIONCLASS1.getName());

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeForTutor_invalidIndexFilteredList_throwsCommandException() {

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.updateCurrentListType(Model.ListType.TUTOR_LIST);
        model.addPerson(TUTOR3);
        model.addTuitionClass(TUITIONCLASS1);
        model.updateCurrentListType(Model.ListType.TUTOR_LIST);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex, TUITIONCLASS1.getName());

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeForStudent_duplicateClassAssign_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.updateCurrentListType(Model.ListType.STUDENT_LIST);
        model.addPerson(STUDENT3);
        model.addTuitionClass(TUITIONCLASS2);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS2.getName());

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void executeForTutor_duplicateClassAssign_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.updateCurrentListType(Model.ListType.TUTOR_LIST);
        model.addPerson(TUTOR1);
        model.addTuitionClass(TUITIONCLASS1);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, TUITIONCLASS1.getName());

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_DUPLICATE_TUTOR);
    }
}
