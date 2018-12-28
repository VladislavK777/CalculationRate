package com.uraltranscom.calculaterate.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 21.12.2018
 *
 * 21.12.2018
 *   1. Версия 1.0
 *
 */

@org.springframework.stereotype.Controller
public class Controller {
    /*// Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private BasicClassImpl basicClassImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "routesFile") MultipartFile routesFile, Model model) {
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/exportWagons", method = RequestMethod.GET)
    public void getXLSWagons(HttpServletResponse response, Model model) {
        basicClassImpl.getClassHandlerLookingFor().fillFinalMapByOrders();
        WriteToFileExcel.downloadWagonsFileExcel(response, basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
    }*/
}
