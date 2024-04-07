import axios from "axios"
import { setInterceptors } from "./interceptor"

function createAaxiosService() {
    const requestService = axios.create({
        baseURL: process.env.VUE_APP_API_URL,
        timeout: 30000,
        withCredentials: true
    });

    return setInterceptors(requestService);
}

const requestService = createAaxiosService();

export default requestService;