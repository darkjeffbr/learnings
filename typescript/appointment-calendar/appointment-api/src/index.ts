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
console.log('Connecting to database ...');
mongoose.connect(`mongodb://${process.env.DB_SERVER}:${process.env.DB_PORT}/appointments`).then(() => {
    console.log('Connected successfully to database');
    console.log('Starting up server ...');
    try {
        app.listen(port, (): void => {
            console.log(`Server listening on port ${port}`);
        });
    } catch (error) {
        console.error(`Error occured: ${error.message}`);
    }
}).catch(error => {
    console.error(`Error occured: ${error}`);
})
