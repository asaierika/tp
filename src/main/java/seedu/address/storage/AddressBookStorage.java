package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {
    enum AddressBookCategories {
        TUTORS, STUDENTS, TUITIONCLASSES
    }

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath(AddressBookCategories cat);

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readAddressBook(AddressBookCategories cat)
            throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath(AddressBookCategories)
     */
    Optional<ReadOnlyAddressBook> readTutorAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath(AddressBookCategories)
     */
    Optional<ReadOnlyAddressBook> readStudentAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath(AddressBookCategories)
     */
    Optional<ReadOnlyAddressBook> readTuitionClassAddressBook(Path filePath)
            throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath(AddressBookCategories)
     * A single addressBook for Tutors, Students and TuitionClasses.
     */
    Optional<ReadOnlyAddressBook> readAllAddressBook() throws DataConversionException, IllegalValueException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
