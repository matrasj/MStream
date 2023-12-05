package pl.matrasj.user.account.validators;

import java.util.function.Predicate;

public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s.length() > 2 && s.length() < 100;
    }
}
