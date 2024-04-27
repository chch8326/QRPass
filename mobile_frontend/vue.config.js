module.exports = {
    lintOnSave: false,
    outputDir: "../backend/src/main/resources/static",
    pluginOptions: {
        vuetify: {}
    },
    devServer: {
        port: process.env.VUE_APP_PORT,
        proxy: {
            "/qrpass": {
                target: process.env.VUE_APP_API_URL,
                changeOrigin: true,
                pathRewrite: {
                    "/^qrpass": ""
                }    
            }    
        }    
    }      
}
