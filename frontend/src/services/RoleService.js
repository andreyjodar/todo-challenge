import BaseService from "./BaseService";

class RoleService extends BaseService {

    constructor() {
        super("/roles");
    }
}

export default RoleService;