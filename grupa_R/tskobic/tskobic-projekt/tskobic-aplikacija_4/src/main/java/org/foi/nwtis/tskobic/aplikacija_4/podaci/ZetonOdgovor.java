package org.foi.nwtis.tskobic.aplikacija_4.podaci;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Klasa ZetonOdgovor.
 *
 * @param zeton žeton
 * @param vrijeme vrijeme
 */
@AllArgsConstructor
public class ZetonOdgovor {
    
    @Getter
    @Setter
    private int zeton;
    
    @Getter
    @Setter
    @NonNull
    private String vrijeme;
}
