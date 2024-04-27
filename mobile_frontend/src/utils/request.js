import axios from 'axios'
import { setInterceptors } from './interceptor'

function createAxiosService() {
    const reqeustService = axios.create({
        baseURL: process.env.VUE_APP_API_URL,
        timeout: 30000,
        withCredentials: true
    });

    return setInterceptors(reqeustService);
}

const reqeustService = createAxiosService();

export default reqeustService;