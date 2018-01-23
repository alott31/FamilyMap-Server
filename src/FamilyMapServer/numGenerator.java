package FamilyMapServer;

import java.util.*;

public class numGenerator
{
    public String generate() {
        long rand = UUID.randomUUID().getLeastSignificantBits();
        String id = String.valueOf(rand);
        return id.toString();
    }

}