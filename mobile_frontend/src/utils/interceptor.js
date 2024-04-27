export function setInterceptors(axiosService) {
    axiosService.interceptors.request.use(
        function(request) {
            return request;
        },

        function(error) {
            return Promise.reject(error);
        }
    );

    axiosService.interceptors.response.use(
        function(response) {
            return response.data;
        },

        function(error) {
            return Promise.reject(error);
        }
    );

    return axiosService;
}