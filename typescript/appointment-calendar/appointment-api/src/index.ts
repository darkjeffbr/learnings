import express, { Application, Request, Response } from "express";
import {routes} from "./routes"

const app: Application = express();
const port = 3000;

app.use(routes);

try {
    app.listen(port, (): void => {
        console.log(`Connected successfully on port ${port}`);
    });
} catch (error) {
    console.error(`Error occured: ${error.message}`);
}

