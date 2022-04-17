import request from './request'

export const RegisterApi = (params) => request.post('/register',params)
