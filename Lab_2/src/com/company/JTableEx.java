package com.company;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class JTableEx {
    JFrame f;
    JTable j;

    JTableEx(String[][] tab, List<String> elements, String[] states, String[] trans, String[][] tab1) {
        f = new JFrame();

        f.setTitle("Transition table");
        System.out.println(tab1[0].length);
        String[][] data = new String[10][tab[0].length+1];

        int x = 0;
        for(int i=0;i<tab1.length;i++){
            if(x==states.length){
                break;
            }
            data[x][0] = states[x];
            for (int j=1;j<= tab1[0].length;j++){
                data[x][j] = tab1[i][j-1];
            }
            x++;
        }

        int k = x;
        for(int i=0;i<10;i++){
            if(i<elements.size() && elements.get(i).length()>1){
                data[k][0] = elements.get(i);
            }else {
                continue;
            }
            for (int j=1;j<= tab[0].length;j++){
                data[k][j] = tab[i][j-1];
            }
            k++;
        }

        String[] columnNames = Stream.concat(Arrays.stream(new String[]{" "}), Arrays.stream(trans))
                .toArray(String[]::new);

        j = new JTable(data, columnNames);
        j.setBounds(60, 80, 400, 600);

        j.setFont(new Font("Serif", Font.PLAIN, 30));
        j.getTableHeader().setFont(new Font("Serif", Font.BOLD, 30));
        j.setRowHeight(50);

        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        f.setSize(600, 400);
        f.setVisible(true);
    }
}