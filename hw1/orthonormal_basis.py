from scipy import linalg
import numpy as np
input = np.array([[1,1,0],[2,0,1],[2,2,1]])  # 书 第8页 例4
output = linalg.orth(input) # 正交单位化
print(np.array(output,dtype=float))  # output即为规范正交基
# print(np.dot(output[0], output[1])) # 验证内积
# l2_axis1 = np.linalg.norm(output, axis=1, ord=2)  # 计算l2范数，验证模长
# print(l2_axis1)