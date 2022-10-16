package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Represents a Teaching Assistant which is-a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TeachingAssistant extends Person {

    private final ModuleCode moduleCode;

    /**
     * Every field must be present and not null.
     */
    public TeachingAssistant(Name name, ModuleCode moduleCode, Phone phone, Email email, Gender gender, Set<Tag> tags,
                             Location location) {
        super(name, phone, email, gender, tags, location);
        this.moduleCode = moduleCode;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeachingAssistant)) {
            return false;
        }

        TeachingAssistant otherPerson = (TeachingAssistant) other;
        return super.equals(other) && otherPerson.getModuleCode().equals(getModuleCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, super.hashCode());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("; Name: ")
            .append(getName())
            .append("; Module Code: ")
            .append(getModuleCode())
            .append("; Phone: ")
            .append(getPhone())
            .append("; Email: ")
            .append(getEmail())
            .append("; Gender: ")
            .append(getGender())
            .append("; Location: ")
            .append(getLocation());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
    @Override
    public int compareModuleCode(Person person) {
        if (person instanceof Professor) {
            return this.moduleCode.toString()
                    .compareTo(((Professor) person).getModuleCode().toString());
        }
        if (person instanceof TeachingAssistant) {
            return this.moduleCode.toString()
                    .compareTo(((TeachingAssistant) person).getModuleCode().toString());
        }
        return 1;
    }

    @Override
    public String getTypeString() {
        return "ta";
    }

    @Override
    public boolean doModulesMatch(Set<String> modulesList, boolean needsAllTags) {
        return modulesList.stream()
                .anyMatch(module -> StringUtil.containsWordIgnoreCase(this.moduleCode.value, module));
    }
}
