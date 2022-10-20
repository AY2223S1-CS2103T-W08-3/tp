package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUBUSERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private PersonMatchesPredicate predicate = new PersonMatchesPredicate();
    private ArgumentMultimap argMultimap;
    private Pattern allArgumentsPattern = Pattern.compile("^all/.+");
    private boolean needsAllTags = false;
    private boolean needsAllModules = false;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_GENDER, PREFIX_TAG, PREFIX_LOCATION, PREFIX_TYPE, PREFIX_GITHUBUSERNAME);

        if (!(areAllArgsValid(PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_GENDER, PREFIX_TAG, PREFIX_LOCATION, PREFIX_TYPE, PREFIX_GITHUBUSERNAME))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicate.setNamesList(getKeywordList(PREFIX_NAME));
        }

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            setModulesList();
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            predicate.setPhonesList(getKeywordList(PREFIX_PHONE));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicate.setEmailsList(getKeywordList(PREFIX_EMAIL));
        }

        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            predicate.setGendersList(getKeywordList(PREFIX_GENDER));
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            predicate.setLocationsList(getKeywordList(PREFIX_LOCATION));
        }

        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            predicate.setTypesList(getKeywordList(PREFIX_TYPE));
        }

        if (argMultimap.getValue(PREFIX_GITHUBUSERNAME).isPresent()) {
            predicate.setUserNamesList(getKeywordList(PREFIX_GITHUBUSERNAME));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            setTagsList();
        }

        return new FindCommand(predicate);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap} and the modules and tag fields have the correct format.
     */
    private boolean areAllArgsValid(Prefix... prefixes) {
        Supplier<Stream<Prefix>> presentArgs = () ->
                Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent());
        if (presentArgs.get().count() == 0) {
            return false;
        } else {
            return presentArgs.get().allMatch(prefix ->
                    argMultimap.getValue(prefix).get().trim().length() != 0)
                    && areTagsArgsValid() && areModulesArgsValid();
        }
    }

    /**
     * Checks if the user's input for the tag field is valid.
     * @return true if the input is valid, false otherwise.
     */
    private boolean areTagsArgsValid() {
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String input = argMultimap.getValue(PREFIX_TAG).get();
            if (input.contains("all/")) {
                needsAllTags = true;
                return allArgumentsPattern.matcher(input.trim()).matches();
            }
        }
        return true;
    }

    /**
     * Checks if the user's input for the module field is valid.
     * @return true if the input is valid, false otherwise.
     */
    private boolean areModulesArgsValid() {
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            String input = argMultimap.getValue(PREFIX_MODULE_CODE).get();
            if (input.contains("all/")) {
                needsAllModules = true;
                return allArgumentsPattern.matcher(input.trim()).matches();
            }
        }
        return true;
    }

    /**
     * Create a List of Strings from the user's input to pass to the {@code PersonMatchesPredicate} object.
     * @param prefix the specified arguments to extract from the {@code }ArgumentMultimap} object
     * @return the List of Strings containing the user's input.
     */
    private List<String> getKeywordList(Prefix prefix) {
        String[] keywordsString = argMultimap.getValue(prefix).get().split("\\s+");
        return Arrays.asList(keywordsString);
    }

    /**
     * Creates and sets the tag set for the {@code PersonMatchesPredicate} object.
     * Checks if the user wants all the tag to match and passes the appropriate
     * argument.
     */
    private void setTagsList() {
        String tagsKeywords = argMultimap.getValue(PREFIX_TAG).get();
        Set<String> tagsKeywordsList;
        if (needsAllTags) {
            tagsKeywordsList = new HashSet<>(Arrays.asList(tagsKeywords.replace("all/", "")
                    .trim().toLowerCase().split("\\s+")));
            predicate.setTagsList(tagsKeywordsList, needsAllTags);
        } else {
            tagsKeywordsList = new HashSet<>(Arrays.asList(tagsKeywords.toLowerCase().split("\\s+")));
            predicate.setTagsList(tagsKeywordsList, needsAllTags);
        }

    }


    /**
     * Creates and sets the module set for the {@code PersonMatchesPredicate} object.
     * Checks if the user wants all the modules to match and passes the appropriate
     * argument.
     */
    private void setModulesList() {
        String modulesKeywords = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        Set<String> modulesKeywordsList;
        if (needsAllModules) {
            modulesKeywordsList = new HashSet<>(Arrays.asList(modulesKeywords.replace("all/", "")
                    .trim().toLowerCase().split("\\s+")));
            predicate.setModulesList(modulesKeywordsList, needsAllModules);
        } else {
            modulesKeywordsList = new HashSet<>(Arrays.asList(modulesKeywords.toLowerCase().split("\\s+")));
            predicate.setModulesList(modulesKeywordsList, needsAllModules);
        }

    }
}
