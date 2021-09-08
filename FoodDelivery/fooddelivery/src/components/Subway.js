import React, { useState } from 'react';
import { Button } from 'react-bootstrap'
import { Link, useHistory } from "react-router-dom"
import axios from "axios";

export default function Subway() {
    const [countAloo, setCountAloo] = useState(0);
    const [countPaneer, setCountPaneer] = useState(0);
    const [countVeggie, setCountVeggie] = useState(0);
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
            aCount: countAloo,
            mCount: countPaneer,
            mcCount: countVeggie
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
            <h1>Welcome to Subway!</h1>
            <br /> <br /> <br />
            <ol>
                <li>AlooPatty Sub &nbsp;   <button onClick={() => setCountAloo(countAloo - 1)}>Remove</button><label>{countAloo}</label> <button onClick={() => setCountAloo(countAloo + 1)}>Add</button></li>
                <li>Paneer Sub &nbsp; <button onClick={() => setCountPaneer(countPaneer - 1)}>Remove</button><label>{countPaneer}</label> <button onClick={() => setCountPaneer(countPaneer + 1)}>Add</button></li>
                <li>Veggie Sub &nbsp; <button onClick={() => setCountVeggie(countVeggie - 1)}>Remove</button><label>{countVeggie}</label> <button onClick={() => setCountVeggie(countVeggie + 1)}>Add</button></li>
            </ol>
            <Button className="d-flex align-items-center justify-content-center w-50" style={{ position: "absolute", left: "20rem" }} onClick={handleSubmit}>Submit</Button>
        </div>
    )
}
