import BaseService from "./BaseService";

class UserService extends BaseService {

    constructor(){
        super("/users");
    }
}
export default UserService;