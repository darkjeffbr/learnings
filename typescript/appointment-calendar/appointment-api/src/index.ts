import express, { Application } from "express";
import {routes} from "./routes"
import { Mongoose } from "mongoose";

const app: Application = express();
const port = 3000;

app.use(express.json());
app.use('/api/v1/', routes);

const mongoose = new Mongoose({ useNewUrlParser: true,  useUnifiedTopology: true});
mongoose.connect('mongodb://localhost:27017/appointments').then()

try {
    app.listen(port, (): void => {
        console.log(`Connected successfully on port ${port}`);
    });
} catch (error) {
    console.error(`Error occured: ${error.message}`);
}

