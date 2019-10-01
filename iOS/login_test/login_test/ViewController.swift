//
//  ViewController.swift
//  login_test
//
//  Created by Jac on 2016/9/26.
//  Copyright © 2016年 Jac. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var acct: UITextField!
    @IBOutlet weak var pw: UITextField!
    @IBOutlet weak var login: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func loginpressed(){
        let account = acct.text!
        let password = pw.text!
        var checker: Bool = false
//        print("這是帳號: \(account)")
//        print("And my password: \(password)")
        
        //開始執行web request
        var request = URLRequest(url: URL(string: "http://140.113.72.29:8100/api/account/?format=json")!)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) {data, response, err in
            do{
                let json = try! JSONSerialization.jsonObject(with: data!)
                print(json)
                
                if let section = json as? NSArray{
                    let n = section.count - 1
                    for i in 0...n{
                        if let profile_data = section[i] as? NSDictionary{
                            if account == profile_data["name"]! as? String{
                                if password == profile_data["pw"]! as? String{
                                    checker = true
                                    print("*****************登入成功******************")
                                }
                                else{
                                    print("***************帳號密碼錯誤****************")
                                }
                            }
                        }
                        
                    }
                    if checker == false{
                        print("************無此帳號************")
                    }
                }
            
            }catch{
                print("Couldn't Serialize")
            }
            }.resume()
    }
}



