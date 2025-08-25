import React from "react";
import Header from "../header/Header";
import Footer from "../footer/Footer";

const LayoutPattern = ({children}) => {

    return (
        <div>
            <Header/>
            {children}
            <Footer/>
        </div>
    );
}
export default LayoutPattern;