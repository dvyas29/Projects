import React, { useState } from 'react';
import { Button } from 'react-bootstrap'
import { Link, useHistory } from "react-router-dom"
import axios from "axios";

export default function Mcd() {

    const [countMcAloo, setCountMcAloo] = useState(0);
    const [countMcSpicy, setCountMcSpicy] = useState(0);
    const [countMaharaja, setCountMaharaja] = useState(0);
    const history = useHistory();


    async function handleSubmit() {
        console.log(localStorage)
        let userData = JSON.parse(localStorage.userData);
        var order = Math.floor((Math.random() * 100) + 1)
        localStorage.setItem("order_id", order)
        const data = {
            name: localStorage.name,
            email_id: userData.email_id,
            order_id: order,
            cStatus: "in_progress",
            aCount: countMcAloo,
            mCount: countMaharaja,
            mcCount: countMcSpicy
        }
        console.log(data)
        await axios.put("https://9x3jukzrb8.execute-api.us-east-1.amazonaws.com/order", data)
            .then((response) => {
                alert("Order placed successfully")
                history.push("/homepage")
            }).catch(function (error) {
                console.log(error);
            });
    }
    return (
        <div>
            <h1>Welcome to MCDonalds</h1>
            <br /> <br /> <br />
            <ol>
                <li>McAloo Tikki Burger &nbsp;   <button onClick={() => setCountMcAloo(countMcAloo - 1)}>Remove</button><label>{countMcAloo}</label> <button onClick={() => setCountMcAloo(countMcAloo + 1)}>Add</button></li>
                <li>McSpicy Paneer Burger &nbsp; <button onClick={() => setCountMcSpicy(countMcSpicy - 1)}>Remove</button><label>{countMcSpicy}</label> <button onClick={() => setCountMcSpicy(countMcSpicy + 1)}>Add</button></li>
                <li>Maharaja Mac Burger &nbsp; <button onClick={() => setCountMaharaja(countMaharaja - 1)}>Remove</button><label>{countMaharaja}</label> <button onClick={() => setCountMaharaja(countMaharaja + 1)}>Add</button></li>
            </ol>
            <Button className="d-flex align-items-center justify-content-center w-50" style={{ position: "absolute", left: "20rem" }} onClick={handleSubmit}>Submit</Button>
        </div>
    )
}
