import express, { Application, Request, Response } from "express";

const app: Application = express();
const port = 3000;

app.get(
    "/",
    (req: Request, res: Response) => {
        return res.status(200).send('Hello Express with Typescript');
    }
);

try {
    app.listen(port, (): void => {
        console.log(`Connected successfully on port ${port}`);
    });
} catch (error) {
    console.error(`Error occured: ${error.message}`);
}

