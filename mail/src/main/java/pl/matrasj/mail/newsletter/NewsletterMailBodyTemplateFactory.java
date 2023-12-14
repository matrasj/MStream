package pl.matrasj.mail.newsletter;

public class NewsletterMailBodyTemplateFactory {
    public static String buildTemplate(String userFirstnameWithLastname) {
        return "<div style=\"font-family:Helvetica, Arial, sans-serif; font-size:16px; margin:0; color:#0b0c0c\">\n" +
                "    <span style=\"display:none; font-size:1px; color:#fff; max-height:0\"></span>\n" +
                "\n" +
                "    <!-- Header Section -->\n" +
                "    <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse; min-width:100%; width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "                    <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse; max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                                    <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr>\n" +
                "                                                <td style=\"padding-left:10px\">\n" +
                "                                                </td>\n" +
                "                                                <td style=\"font-size:28px; line-height:1.315789474; Margin-top:4px; padding-left:10px\">\n" +
                "                                                    <span style=\"font-family:Helvetica, Arial, sans-serif; font-weight:700; color:#ffffff; text-decoration:none; vertical-align:top; display:inline-block\">Newsletter Confirmation</span>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "\n" +
                "    <!-- Divider Section -->\n" +
                "    <table role=\"presentation\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; max-width:580px; width:100%!important\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "                <td>\n" +
                "                    <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "\n" +
                "    <!-- Main Content Section -->\n" +
                "    <table role=\"presentation\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; max-width:580px; width:100%!important\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td height=\"30\"><br></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "                <td style=\"font-family:Helvetica, Arial, sans-serif; font-size:19px; line-height:1.315789474; max-width:560px\">\n" +
                "                    <p style=\"Margin:0 0 20px 0; font-size:19px; line-height:25px; color:#0b0c0c\">Hi " +  userFirstnameWithLastname + ",</p>\n" +
                "                    <p style=\"Margin:0 0 20px 0; font-size:19px; line-height:25px; color:#0b0c0c\">Thank you for subscribing to our Java Newsletter. In this edition, let's explore the powerful Stream API introduced in Java 8.</p>\n" +
                "                    <p>The Stream API allows you to process sequences of elements in a functional style. It provides a concise and expressive way to perform operations on collections, making your code more readable and efficient.</p>\n" +
                "                    <p>Stay tuned for more Java insights and updates in our upcoming newsletters!</p>\n" +
                "                </td>\n" +
                "                <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td height=\"30\"><br></td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "    <div class=\"yj6qo\"></div>\n" +
                "    <div class=\"adL\"></div>\n" +
                "</div>\n";
    }
}
