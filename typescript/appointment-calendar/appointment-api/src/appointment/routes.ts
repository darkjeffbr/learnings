import { Request, Response, Router } from "express";
import { controller } from "./module";

export const routes: Router = Router();

routes.get('/', (req: Request, res: Response) => {
    controller.getAll(req, res);
});

routes.post('/', (req: Request, res: Response) => {
    controller.save(req, res);
});

routes.get('/:id', (req: Request, res: Response) => {
    controller.getById(req, res);
});

routes.put('/:id', (req: Request, res: Response) => {
    controller.update(req, res);
});