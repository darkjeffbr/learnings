import dotenv  from "dotenv"
import express, { Application } from "express";
import {routes} from "./routes"
import { Mongoose } from "mongoose";

dotenv.config();

const app: Application = express();
const port = process.env.SERVER_PORT;

app.use(express.json());
app.use('/api/v1/', routes);

const mongoose = new Mongoose({ useNewUrlParser: true,  useUnifiedTopology: true});
mongoose.connect(`mongodb://${process.env.DB_SERVER}:${process.env.DB_PORT}/appointments`).then()

try {
    app.listen(port, (): void => {
        console.log(`Connected successfully on port ${port}`);
    });
} catch (error) {
    console.error(`Error occured: ${error.message}`);
}

