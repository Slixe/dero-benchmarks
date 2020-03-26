package config;

import java.util.concurrent.TimeUnit

import fr.litarvan.paladin.http.AcceptCrossOriginRequestsMiddleware
import fr.slixe.benchmarks.http.AuthMiddleware
import fr.slixe.benchmarks.http.controller.AuthController
import fr.slixe.benchmarks.http.controller.MainController

[
    sessionDuration: TimeUnit.DAYS.toMillis(61), // 2 Months

    /**
     * The app controllers, call them whatever you want to
     */
    controllers: [
        main: MainController,
		auth: AuthController
    ],

	routeMiddlewares: [
		auth: AuthMiddleware
	],
	
    /**
     * Global middlewares (applied on all routes)
     */
    globalMiddlewares: [
        // Remove comment if you want your API to be callable from any website
        AcceptCrossOriginRequestsMiddleware
    ]
]