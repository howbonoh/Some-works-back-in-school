//
//  ViewController.swift
//  put_request_test
//
//  Created by Jac on 2016/10/12.
//  Copyright © 2016年 Jac. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    let ID = 4
    @IBOutlet weak var run: UIButton!
    @IBOutlet var rewrite: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // press button to rewrite the value
    @IBAction func running(_ sender: AnyObject) {
        let id = ID.description
        let new_element = rewrite.text
        var request = URLRequest(url: URL(string: "http://140.113.72.29:8100/api/account/"+id+"/")! as URL)
        request.httpMethod = "PUT"
        
        let params = NSMutableDictionary()
        params.setValue(new_element, forKey: "realname")
        print("json content")
        print(params)
        request.httpBody = try! JSONSerialization.data(withJSONObject: params, options: JSONSerialization.WritingOptions.prettyPrinted)
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
        URLSession.shared.dataTask(with: request) {data, response, err in
            }.resume()
    }
        
    
}

