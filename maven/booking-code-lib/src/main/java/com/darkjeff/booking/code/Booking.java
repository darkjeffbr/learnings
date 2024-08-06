package com.darkjeff.booking.code;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Booking {
    private static final List<Character> SYMBOLS = "0123456789ABCDEFGHJKLMNPQRTUVWXY".chars().mapToObj(c -> (char) c).toList();
    private static final Random random = new Random();

    public String generate() {
        return generate(8, CodeType.MIXED);
    }

    public String generate(Integer limit, CodeType codeType) {
        var sb = new StringBuilder();
        for(var i = 0; i < limit; i++) {
            var codeTypePattern = Pattern.compile(codeType.getPattern());
            String nextSymbol;
            do {
                var index = random.nextInt(SYMBOLS.size());
                nextSymbol = SYMBOLS.get(index).toString();
            } while( ! codeTypePattern.matcher(nextSymbol).matches());
            sb.append(nextSymbol);
        }
        return sb.toString();
    }

    public String generate(Integer limit) {
        return generate(limit, CodeType.MIXED);
    }

    public String generate(CodeType codeType) {
        return generate(8, codeType);
    }

}
