package net.octopusmc.utils.misc;

import org.bukkit.ChatColor;

public class BlinkEffect {

    private String objective;

    private String name;
    private ChatColor currentColor;
    private ChatColor otherColor;
    private ChatColor c33;
    private int index = 0;

    public BlinkEffect(String title, ChatColor c1, ChatColor c2, ChatColor c3)
    {
        this.currentColor = c1;
        this.otherColor = c2;
        this.c33 = c3;
        this.name = title;
    }

    private void nextP() {
        if ( index < name.length() && index > 0)
        {
            objective = otherColor.toString() + ChatColor.BOLD + name.substring( 0, index )
                    + currentColor.toString() + ChatColor.BOLD + name.substring( index, index )
                    +  c33.toString() + ChatColor.BOLD + name.substring( index, index+1 )
                    + currentColor.toString() + ChatColor.BOLD + name.substring( index+1, name.length() );
        }
        if ( index == name.length() )
        {
            objective = otherColor.toString() + ChatColor.BOLD + name.substring( 0, name.length() );
            ChatColor tempColor = currentColor;
            currentColor = otherColor;
            otherColor = tempColor;
        }

        if(index == name.length()+40) {
            index = 0;
        }

        index++;
    }

    public String next()
    {
        nextP();

        return "" + objective;
    }

}
