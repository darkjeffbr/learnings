const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin")

module.exports = {
    entry: "./src/index.js", // entry point for the app
    mode: "development",
    output: {
        filename: "bundle.js", // name of the file where bundled code will go, created by webpack
        path: path.resolve(__dirname, "public"), // where should this bundled app be created, created by webpack
        clean: true
    },
    module: {
        rules: [
            {
                test: /\.jsx?$/, // what kind of files to give to babel, matches .js and .jsx only
                exclude: /node_modules/, // we don't want to process files inside node_modules
                use: "babel-loader", // we want to use babel loader when we come across .js and .jsx files (text line)
            },
            {
                test: /\.css$/, // matches .css files only
                use: ["style-loader", "css-loader"], // we want to use css-loader and style-loader for css files
            },
        ]
    },
    plugins: [new HtmlWebpackPlugin({
        template: path.resolve(__dirname, "utils/index.html"),
    })],
    devServer: {
        port: 3000, // port where the app will be available
        static: "./public", // folder to watch for constant changes for reloading
    },
};