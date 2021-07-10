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

import { Request, Response, Router } from "express";
import  * as AppointmentController from "./controller/appointment-controller";

export const routes: Router = Router();

routes.get('/', (req: Request, res: Response) => {
    AppointmentController.getAll(req, res);
});