module.exports = {
    lintOnSave: false,
    outputDir: "../backend/src/main/resources/static",
    devServer: {
        port: process.env.VUE_APP_PORT,
        proxy: {
            "/pres": {
                target: process.env.VUE_APP_API_URL,
                changeOrigin: true,
                pathRewrite: {
                    "/^pres": ""
                }
            }
        }
    }
}