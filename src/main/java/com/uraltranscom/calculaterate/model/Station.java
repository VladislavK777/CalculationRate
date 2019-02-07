package com.uraltranscom.calculaterate.model;

import lombok.*;

/**
 *
 * Класс Станция
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private String idStation; //Код станции
    private String nameStation; //Название станция
    private Road road; //Дорога станции
}
