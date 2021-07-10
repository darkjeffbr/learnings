/*
import { Application, Request, Response } from "express";
import {* as AppointmentController} from "./controller/appointment-controller";
export const register = (app: Application) => {
    app.get('/', (req: Request, res: Response)=>{
        AppointmentController.get(req, res);
    });
};

// index.ts
import * as routes from "./routes";

routes.register(app);
*/

import { Router } from "express";
import { routes as AppointmentRoutes } from "./appointment/routes";

export const routes: Router = Router();

routes.use('/appointments', AppointmentRoutes);