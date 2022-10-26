package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TUITIONCLASSES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORS_LISTED_OVERVIEW;

import java.util.HashMap;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.Model.ListType;
import seedu.address.model.StudentContainsKeywordsPredicate;
import seedu.address.model.TuitionClassContainsKeywordsPredicate;
import seedu.address.model.TutorContainsKeywordsPredicate;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.tutor.Tutor;
import seedu.address.model.tuitionclass.TuitionClass;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students / tutors / tuition classes "
            + "(depending on current displayed list)\n"
            + "whose fields contain the specified keywords (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/KEYWORDS [MORE PREFIX/KEYWORDS]...\n"
            + "Example for Student: " + COMMAND_WORD + " n/alice e/alice@example.com sch/Keming\n"
            + "Example for Tutor: " + COMMAND_WORD + " n/john a/clementi q/computing\n"
            + "Example for Tuition Class: " + COMMAND_WORD + " n/P2Math d/tuesday sub/mathematics t/18:00\n";

    private final HashMap<Prefix, String> keywords;

    private StudentContainsKeywordsPredicate<Student> studentPredicate;
    private TutorContainsKeywordsPredicate<Tutor> tutorPredicate;
    private TuitionClassContainsKeywordsPredicate<TuitionClass> classPredicate;

    public FindCommand(HashMap<Prefix, String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ListType type = model.getCurrentListType();

        switch (type) {
        case STUDENT_LIST:
            this.studentPredicate = new StudentContainsKeywordsPredicate<>(keywords);
            model.updateFilteredStudentList(studentPredicate);
            return new CommandResult(
                    String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));

        case TUTOR_LIST:
            this.tutorPredicate = new TutorContainsKeywordsPredicate<>(keywords);
            model.updateFilteredTutorList(tutorPredicate);
            return new CommandResult(
                    String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, model.getFilteredTutorList().size()));

        default:
            this.classPredicate = new TuitionClassContainsKeywordsPredicate<>(keywords);
            model.updateFilteredTuitionClassList(classPredicate);
            return new CommandResult(
                    String.format(MESSAGE_TUITIONCLASSES_LISTED_OVERVIEW,
                            model.getFilteredTuitionClassList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof FindCommand) {
            FindCommand otherFind = (FindCommand) other;

            if (this.studentPredicate != null) {
                if (otherFind.studentPredicate != null) {
                    return this.studentPredicate.equals(((FindCommand) other).studentPredicate);
                }
            } else if (this.tutorPredicate != null) {
                if (otherFind.tutorPredicate != null) {
                    return this.tutorPredicate.equals(otherFind.tutorPredicate);
                }
            } else if (this.classPredicate != null) {
                if (otherFind.classPredicate != null) {
                    return this.classPredicate.equals(otherFind.classPredicate);
                }
            }
            return this.keywords.equals(otherFind.keywords);
        }
        return false;
    }
}
